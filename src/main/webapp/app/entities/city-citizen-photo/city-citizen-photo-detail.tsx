import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './city-citizen-photo.reducer';

export const CityCitizenPhotoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cityCitizenPhotoEntity = useAppSelector(state => state.cityCitizenPhoto.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cityCitizenPhotoDetailsHeading">
          <Translate contentKey="sousseMaVilleApp.cityCitizenPhoto.detail.title">CityCitizenPhoto</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cityCitizenPhotoEntity.id}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="sousseMaVilleApp.cityCitizenPhoto.image">Image</Translate>
            </span>
          </dt>
          <dd>
            {cityCitizenPhotoEntity.image ? (
              <div>
                {cityCitizenPhotoEntity.imageContentType ? (
                  <a onClick={openFile(cityCitizenPhotoEntity.imageContentType, cityCitizenPhotoEntity.image)}>
                    <img
                      src={`data:${cityCitizenPhotoEntity.imageContentType};base64,${cityCitizenPhotoEntity.image}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {cityCitizenPhotoEntity.imageContentType}, {byteSize(cityCitizenPhotoEntity.image)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/city-citizen-photo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/city-citizen-photo/${cityCitizenPhotoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CityCitizenPhotoDetail;
