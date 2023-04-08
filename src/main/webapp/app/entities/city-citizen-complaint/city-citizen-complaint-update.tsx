import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IComplaintCategory } from 'app/shared/model/complaint-category.model';
import { getEntities as getComplaintCategories } from 'app/entities/complaint-category/complaint-category.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';
import { getEntities as getCityCitizenPhotos } from 'app/entities/city-citizen-photo/city-citizen-photo.reducer';
import { ICityCitizenComplaint } from 'app/shared/model/city-citizen-complaint.model';
import { Complaintstate } from 'app/shared/model/enumerations/complaintstate.model';
import { getEntity, updateEntity, createEntity, reset } from './city-citizen-complaint.reducer';

export const CityCitizenComplaintUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const complaintCategories = useAppSelector(state => state.complaintCategory.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const cityCitizenPhotos = useAppSelector(state => state.cityCitizenPhoto.entities);
  const cityCitizenComplaintEntity = useAppSelector(state => state.cityCitizenComplaint.entity);
  const loading = useAppSelector(state => state.cityCitizenComplaint.loading);
  const updating = useAppSelector(state => state.cityCitizenComplaint.updating);
  const updateSuccess = useAppSelector(state => state.cityCitizenComplaint.updateSuccess);
  const complaintstateValues = Object.keys(Complaintstate);

  const handleClose = () => {
    navigate('/city-citizen-complaint' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getComplaintCategories({}));
    dispatch(getUsers({}));
    dispatch(getCityCitizenPhotos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cityCitizenComplaintEntity,
      ...values,
      cityCitizenPhotos: mapIdList(values.cityCitizenPhotos),
      complaintCategory: complaintCategories.find(it => it.id.toString() === values.complaintCategory.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          complaintstate: 'RECEIVED',
          ...cityCitizenComplaintEntity,
          complaintCategory: cityCitizenComplaintEntity?.complaintCategory?.id,
          user: cityCitizenComplaintEntity?.user?.id,
          cityCitizenPhotos: cityCitizenComplaintEntity?.cityCitizenPhotos?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="sousseMaVilleApp.cityCitizenComplaint.home.createOrEditLabel" data-cy="CityCitizenComplaintCreateUpdateHeading">
            <Translate contentKey="sousseMaVilleApp.cityCitizenComplaint.home.createOrEditLabel">
              Create or edit a CityCitizenComplaint
            </Translate>
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
                  id="city-citizen-complaint-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.address')}
                id="city-citizen-complaint-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.description')}
                id="city-citizen-complaint-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.date')}
                id="city-citizen-complaint-date"
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.sharewithpublic')}
                id="city-citizen-complaint-sharewithpublic"
                name="sharewithpublic"
                data-cy="sharewithpublic"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.complaintstate')}
                id="city-citizen-complaint-complaintstate"
                name="complaintstate"
                data-cy="complaintstate"
                type="select"
              >
                {complaintstateValues.map(complaintstate => (
                  <option value={complaintstate} key={complaintstate}>
                    {translate('sousseMaVilleApp.Complaintstate.' + complaintstate)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.firstname')}
                id="city-citizen-complaint-firstname"
                name="firstname"
                data-cy="firstname"
                type="text"
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.lastname')}
                id="city-citizen-complaint-lastname"
                name="lastname"
                data-cy="lastname"
                type="text"
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.email')}
                id="city-citizen-complaint-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.phonenumber')}
                id="city-citizen-complaint-phonenumber"
                name="phonenumber"
                data-cy="phonenumber"
                type="text"
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.googlemapsx')}
                id="city-citizen-complaint-googlemapsx"
                name="googlemapsx"
                data-cy="googlemapsx"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.googlemapy')}
                id="city-citizen-complaint-googlemapy"
                name="googlemapy"
                data-cy="googlemapy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="city-citizen-complaint-complaintCategory"
                name="complaintCategory"
                data-cy="complaintCategory"
                label={translate('sousseMaVilleApp.cityCitizenComplaint.complaintCategory')}
                type="select"
                required
              >
                <option value="" key="0" />
                {complaintCategories
                  ? complaintCategories.map(otherEntity => (
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
                id="city-citizen-complaint-user"
                name="user"
                data-cy="user"
                label={translate('sousseMaVilleApp.cityCitizenComplaint.user')}
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('sousseMaVilleApp.cityCitizenComplaint.cityCitizenPhoto')}
                id="city-citizen-complaint-cityCitizenPhoto"
                data-cy="cityCitizenPhoto"
                type="select"
                multiple
                name="cityCitizenPhotos"
              >
                <option value="" key="0" />
                {cityCitizenPhotos
                  ? cityCitizenPhotos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/city-citizen-complaint" replace color="info">
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

export default CityCitizenComplaintUpdate;
