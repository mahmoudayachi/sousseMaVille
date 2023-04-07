import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CityCitizenPhoto from './city-citizen-photo';
import CityCitizenPhotoDetail from './city-citizen-photo-detail';
import CityCitizenPhotoUpdate from './city-citizen-photo-update';
import CityCitizenPhotoDeleteDialog from './city-citizen-photo-delete-dialog';

const CityCitizenPhotoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CityCitizenPhoto />} />
    <Route path="new" element={<CityCitizenPhotoUpdate />} />
    <Route path=":id">
      <Route index element={<CityCitizenPhotoDetail />} />
      <Route path="edit" element={<CityCitizenPhotoUpdate />} />
      <Route path="delete" element={<CityCitizenPhotoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CityCitizenPhotoRoutes;
