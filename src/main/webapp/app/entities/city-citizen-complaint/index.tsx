import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CityCitizenComplaint from './city-citizen-complaint';
import CityCitizenComplaintDetail from './city-citizen-complaint-detail';
import CityCitizenComplaintUpdate from './city-citizen-complaint-update';
import CityCitizenComplaintDeleteDialog from './city-citizen-complaint-delete-dialog';

const CityCitizenComplaintRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CityCitizenComplaint />} />
    <Route path="new" element={<CityCitizenComplaintUpdate />} />
    <Route path=":id">
      <Route index element={<CityCitizenComplaintDetail />} />
      <Route path="edit" element={<CityCitizenComplaintUpdate />} />
      <Route path="delete" element={<CityCitizenComplaintDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CityCitizenComplaintRoutes;
