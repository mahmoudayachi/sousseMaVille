import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ComplaintCategory from './complaint-category';
import ComplaintCategoryDetail from './complaint-category-detail';

const ComplaintCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ComplaintCategory />} />
    <Route path=":id">
      <Route index element={<ComplaintCategoryDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ComplaintCategoryRoutes;
