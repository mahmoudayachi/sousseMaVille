import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserRole from './user-role';
import UserRoleDetail from './user-role-detail';

const UserRoleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserRole />} />
    <Route path=":id">
      <Route index element={<UserRoleDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserRoleRoutes;
