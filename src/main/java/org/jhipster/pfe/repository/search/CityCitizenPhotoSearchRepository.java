package org.jhipster.pfe.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.jhipster.pfe.domain.CityCitizenPhoto;
import org.jhipster.pfe.repository.CityCitizenPhotoRepository;
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
 * Spring Data Elasticsearch repository for the {@link CityCitizenPhoto} entity.
 */
public interface CityCitizenPhotoSearchRepository
    extends ElasticsearchRepository<CityCitizenPhoto, Long>, CityCitizenPhotoSearchRepositoryInternal {}

interface CityCitizenPhotoSearchRepositoryInternal {
    Page<CityCitizenPhoto> search(String query, Pageable pageable);

    Page<CityCitizenPhoto> search(Query query);

    void index(CityCitizenPhoto entity);
}

class CityCitizenPhotoSearchRepositoryInternalImpl implements CityCitizenPhotoSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final CityCitizenPhotoRepository repository;

    CityCitizenPhotoSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, CityCitizenPhotoRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<CityCitizenPhoto> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<CityCitizenPhoto> search(Query query) {
        SearchHits<CityCitizenPhoto> searchHits = elasticsearchTemplate.search(query, CityCitizenPhoto.class);
        List<CityCitizenPhoto> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(CityCitizenPhoto entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
