import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './complaint-category.reducer';

export const ComplaintCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const complaintCategoryEntity = useAppSelector(state => state.complaintCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="complaintCategoryDetailsHeading">
          <Translate contentKey="sousseMaVilleApp.complaintCategory.detail.title">ComplaintCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{complaintCategoryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="sousseMaVilleApp.complaintCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{complaintCategoryEntity.name}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="sousseMaVilleApp.complaintCategory.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{complaintCategoryEntity.icon}</dd>
        </dl>
        <Button tag={Link} to="/complaint-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/complaint-category/${complaintCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ComplaintCategoryDetail;
