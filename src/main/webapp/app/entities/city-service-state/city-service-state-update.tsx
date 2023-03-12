import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICityServiceState } from 'app/shared/model/city-service-state.model';
import { state } from 'app/shared/model/enumerations/state.model';
import { getEntity, updateEntity, createEntity, reset } from './city-service-state.reducer';

export const CityServiceStateUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cityServiceStateEntity = useAppSelector(state => state.cityServiceState.entity);
  const loading = useAppSelector(state => state.cityServiceState.loading);
  const updating = useAppSelector(state => state.cityServiceState.updating);
  const updateSuccess = useAppSelector(state => state.cityServiceState.updateSuccess);
  const stateValues = Object.keys(state);

  const handleClose = () => {
    navigate('/city-service-state' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cityServiceStateEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          name: 'ACTIVATED',
          ...cityServiceStateEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sousseMaVilleApp.cityServiceState.home.createOrEditLabel" data-cy="CityServiceStateCreateUpdateHeading">
            <Translate contentKey="sousseMaVilleApp.cityServiceState.home.createOrEditLabel">Create or edit a CityServiceState</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="city-service-state-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sousseMaVilleApp.cityServiceState.name')}
                id="city-service-state-name"
                name="name"
                data-cy="name"
                type="select"
              >
                {stateValues.map(state => (
                  <option value={state} key={state}>
                    {translate('sousseMaVilleApp.state.' + state)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/city-service-state" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CityServiceStateUpdate;
