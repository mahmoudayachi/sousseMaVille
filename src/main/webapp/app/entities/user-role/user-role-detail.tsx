import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-role.reducer';

export const UserRoleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userRoleEntity = useAppSelector(state => state.userRole.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRoleDetailsHeading">
          <Translate contentKey="sousseMaVilleApp.userRole.detail.title">UserRole</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userRoleEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="sousseMaVilleApp.userRole.name">Name</Translate>
            </span>
          </dt>
          <dd>{userRoleEntity.name}</dd>
          <dt>
            <Translate contentKey="sousseMaVilleApp.userRole.userrole">Userrole</Translate>
          </dt>
          <dd>
            {userRoleEntity.userroles
              ? userRoleEntity.userroles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {userRoleEntity.userroles && i === userRoleEntity.userroles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/user-role" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-role/${userRoleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRoleDetail;
