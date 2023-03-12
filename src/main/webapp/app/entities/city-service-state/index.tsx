import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CityServiceState from './city-service-state';
import CityServiceStateDetail from './city-service-state-detail';
import CityServiceStateUpdate from './city-service-state-update';
import CityServiceStateDeleteDialog from './city-service-state-delete-dialog';

const CityServiceStateRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CityServiceState />} />
    <Route path="new" element={<CityServiceStateUpdate />} />
    <Route path=":id">
      <Route index element={<CityServiceStateDetail />} />
      <Route path="edit" element={<CityServiceStateUpdate />} />
      <Route path="delete" element={<CityServiceStateDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CityServiceStateRoutes;
