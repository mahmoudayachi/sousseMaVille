import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './city-citizen-complaint.reducer';

export const CityCitizenComplaintDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cityCitizenComplaintEntity = useAppSelector(state => state.cityCitizenComplaint.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cityCitizenComplaintDetailsHeading">
          <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.detail.title">CityCitizenComplaint</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.id}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.address">Address</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.address}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.description">Description</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.description}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {cityCitizenComplaintEntity.date ? (
              <TextFormat value={cityCitizenComplaintEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="sharewithpublic">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.sharewithpublic">Sharewithpublic</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.sharewithpublic ? 'true' : 'false'}</dd>
          <dt>
            <span id="complaintstate">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.complaintstate">Complaintstate</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.complaintstate}</dd>
          <dt>
            <span id="firstname">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.firstname">Firstname</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.firstname}</dd>
          <dt>
            <span id="lastname">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.lastname">Lastname</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.lastname}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.email">Email</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.email}</dd>
          <dt>
            <span id="phonenumber">
              <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.phonenumber">Phonenumber</Translate>
            </span>
          </dt>
          <dd>{cityCitizenComplaintEntity.phonenumber}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.complaintCategory">Complaint Category</Translate>
          </dt>
          <dd>{cityCitizenComplaintEntity.complaintCategory ? cityCitizenComplaintEntity.complaintCategory.name : ''}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.user">User</Translate>
          </dt>
          <dd>{cityCitizenComplaintEntity.user ? cityCitizenComplaintEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.cityCitizenPhoto">City Citizen Photo</Translate>
          </dt>
          <dd>
            {cityCitizenComplaintEntity.cityCitizenPhotos
              ? cityCitizenComplaintEntity.cityCitizenPhotos.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {cityCitizenComplaintEntity.cityCitizenPhotos && i === cityCitizenComplaintEntity.cityCitizenPhotos.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/city-citizen-complaint" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/city-citizen-complaint/${cityCitizenComplaintEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CityCitizenComplaintDetail;
