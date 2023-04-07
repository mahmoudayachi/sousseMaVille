import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CityService from './city-service';
import CityServiceState from './city-service-state';
import UserRole from './user-role';
import ComplaintCategory from './complaint-category';
import CityCitizenComplaint from './city-citizen-complaint';
import CityCitizenPhoto from './city-citizen-photo';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="city-service/*" element={<CityService />} />
        <Route path="city-service-state/*" element={<CityServiceState />} />
        <Route path="user-role/*" element={<UserRole />} />
        <Route path="complaint-category/*" element={<ComplaintCategory />} />
        <Route path="city-citizen-complaint/*" element={<CityCitizenComplaint />} />
        <Route path="city-citizen-photo/*" element={<CityCitizenPhoto />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
