import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CityService from './city-service';
import CityServiceDetail from './city-service-detail';
import CityServiceUpdate from './city-service-update';
import CityServiceDeleteDialog from './city-service-delete-dialog';

const CityServiceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CityService />} />
    <Route path="new" element={<CityServiceUpdate />} />
    <Route path=":id">
      <Route index element={<CityServiceDetail />} />
      <Route path="edit" element={<CityServiceUpdate />} />
      <Route path="delete" element={<CityServiceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CityServiceRoutes;
