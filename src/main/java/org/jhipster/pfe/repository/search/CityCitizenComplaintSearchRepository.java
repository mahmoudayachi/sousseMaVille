package org.jhipster.pfe.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.jhipster.pfe.domain.CityCitizenComplaint;
import org.jhipster.pfe.repository.CityCitizenComplaintRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data Elasticsearch repository for the {@link CityCitizenComplaint} entity.
 */
public interface CityCitizenComplaintSearchRepository
    extends ElasticsearchRepository<CityCitizenComplaint, Long>, CityCitizenComplaintSearchRepositoryInternal {}

interface CityCitizenComplaintSearchRepositoryInternal {
    Page<CityCitizenComplaint> search(String query, Pageable pageable);

    Page<CityCitizenComplaint> search(Query query);

    void index(CityCitizenComplaint entity);
}

class CityCitizenComplaintSearchRepositoryInternalImpl implements CityCitizenComplaintSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final CityCitizenComplaintRepository repository;

    CityCitizenComplaintSearchRepositoryInternalImpl(
        ElasticsearchRestTemplate elasticsearchTemplate,
        CityCitizenComplaintRepository repository
    ) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<CityCitizenComplaint> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<CityCitizenComplaint> search(Query query) {
        SearchHits<CityCitizenComplaint> searchHits = elasticsearchTemplate.search(query, CityCitizenComplaint.class);
        List<CityCitizenComplaint> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(CityCitizenComplaint entity) {
        repository.findOneWithEagerRelationships(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
