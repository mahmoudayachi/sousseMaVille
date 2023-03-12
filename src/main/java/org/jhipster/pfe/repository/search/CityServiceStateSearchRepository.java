package org.jhipster.pfe.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.jhipster.pfe.domain.CityServiceState;
import org.jhipster.pfe.repository.CityServiceStateRepository;
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
 * Spring Data Elasticsearch repository for the {@link CityServiceState} entity.
 */
public interface CityServiceStateSearchRepository
    extends ElasticsearchRepository<CityServiceState, Long>, CityServiceStateSearchRepositoryInternal {}

interface CityServiceStateSearchRepositoryInternal {
    Page<CityServiceState> search(String query, Pageable pageable);

    Page<CityServiceState> search(Query query);

    void index(CityServiceState entity);
}

class CityServiceStateSearchRepositoryInternalImpl implements CityServiceStateSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final CityServiceStateRepository repository;

    CityServiceStateSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, CityServiceStateRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<CityServiceState> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<CityServiceState> search(Query query) {
        SearchHits<CityServiceState> searchHits = elasticsearchTemplate.search(query, CityServiceState.class);
        List<CityServiceState> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(CityServiceState entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
