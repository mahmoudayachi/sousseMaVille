import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICityServiceState } from 'app/shared/model/city-service-state.model';
import { getEntities as getCityServiceStates } from 'app/entities/city-service-state/city-service-state.reducer';
import { IUserRole } from 'app/shared/model/user-role.model';
import { getEntities as getUserRoles } from 'app/entities/user-role/user-role.reducer';
import { ICityService } from 'app/shared/model/city-service.model';
import { getEntity, updateEntity, createEntity, reset } from './city-service.reducer';

export const CityServiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const cityServiceStates = useAppSelector(state => state.cityServiceState.entities);
  const userRoles = useAppSelector(state => state.userRole.entities);
  const cityServiceEntity = useAppSelector(state => state.cityService.entity);
  const loading = useAppSelector(state => state.cityService.loading);
  const updating = useAppSelector(state => state.cityService.updating);
  const updateSuccess = useAppSelector(state => state.cityService.updateSuccess);

  const handleClose = () => {
    navigate('/city-service' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCityServiceStates({}));
    dispatch(getUserRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cityServiceEntity,
      ...values,
      userroles: mapIdList(values.userroles),
      cityservicestate: cityServiceStates.find(it => it.id.toString() === values.cityservicestate.toString()),
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
          ...cityServiceEntity,
          cityservicestate: cityServiceEntity?.cityservicestate?.id,
          userroles: cityServiceEntity?.userroles?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sousseMaVilleApp.cityService.home.createOrEditLabel" data-cy="CityServiceCreateUpdateHeading">
            <Translate contentKey="sousseMaVilleApp.cityService.home.createOrEditLabel">Create or edit a CityService</Translate>
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
                  id="city-service-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.title')}
                id="city-service-title"
                name="title"
                data-cy="title"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.description')}
                id="city-service-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 2000, message: translate('entity.validation.maxlength', { max: 2000 }) },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.tooltip')}
                id="city-service-tooltip"
                name="tooltip"
                data-cy="tooltip"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.icon')}
                id="city-service-icon"
                name="icon"
                data-cy="icon"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.order')}
                id="city-service-order"
                name="order"
                data-cy="order"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1, message: translate('entity.validation.min', { min: 1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="city-service-cityservicestate"
                name="cityservicestate"
                data-cy="cityservicestate"
                label={translate('sousseMaVilleApp.cityService.cityservicestate')}
                type="select"
                required
              >
                <option value="" key="0" />
                {cityServiceStates
                  ? cityServiceStates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('sousseMaVilleApp.cityService.userrole')}
                id="city-service-userrole"
                data-cy="userrole"
                type="select"
                multiple
                name="userroles"
              >
                <option value="" key="0" />
                {userRoles
                  ? userRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/city-service" replace color="info">
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

export default CityServiceUpdate;
