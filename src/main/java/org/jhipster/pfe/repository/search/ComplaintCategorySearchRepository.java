package org.jhipster.pfe.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.jhipster.pfe.domain.ComplaintCategory;
import org.jhipster.pfe.repository.ComplaintCategoryRepository;
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
 * Spring Data Elasticsearch repository for the {@link ComplaintCategory} entity.
 */
public interface ComplaintCategorySearchRepository
    extends ElasticsearchRepository<ComplaintCategory, Long>, ComplaintCategorySearchRepositoryInternal {}

interface ComplaintCategorySearchRepositoryInternal {
    Page<ComplaintCategory> search(String query, Pageable pageable);

    Page<ComplaintCategory> search(Query query);

    void index(ComplaintCategory entity);
}

class ComplaintCategorySearchRepositoryInternalImpl implements ComplaintCategorySearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final ComplaintCategoryRepository repository;

    ComplaintCategorySearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, ComplaintCategoryRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<ComplaintCategory> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<ComplaintCategory> search(Query query) {
        SearchHits<ComplaintCategory> searchHits = elasticsearchTemplate.search(query, ComplaintCategory.class);
        List<ComplaintCategory> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(ComplaintCategory entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
