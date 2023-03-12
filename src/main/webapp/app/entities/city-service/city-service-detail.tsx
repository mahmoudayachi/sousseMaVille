import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './city-service.reducer';

export const CityServiceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cityServiceEntity = useAppSelector(state => state.cityService.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cityServiceDetailsHeading">
          <Translate contentKey="sousseMaVilleApp.cityService.detail.title">CityService</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="sousseMaVilleApp.cityService.title">Title</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="sousseMaVilleApp.cityService.description">Description</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.description}</dd>
          <dt>
            <span id="tooltip">
              <Translate contentKey="sousseMaVilleApp.cityService.tooltip">Tooltip</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.tooltip}</dd>
          <dt>
            <span id="icon">
              <Translate contentKey="sousseMaVilleApp.cityService.icon">Icon</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.icon}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="sousseMaVilleApp.cityService.order">Order</Translate>
            </span>
          </dt>
          <dd>{cityServiceEntity.order}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.cityService.cityservicestate">Cityservicestate</Translate>
          </dt>
          <dd>{cityServiceEntity.cityservicestate ? cityServiceEntity.cityservicestate.name : ''}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.cityService.userrole">Userrole</Translate>
          </dt>
          <dd>
            {cityServiceEntity.userroles
              ? cityServiceEntity.userroles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {cityServiceEntity.userroles && i === cityServiceEntity.userroles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/city-service" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/city-service/${cityServiceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CityServiceDetail;
