import cityService from 'app/entities/city-service/city-service.reducer';
import cityServiceState from 'app/entities/city-service-state/city-service-state.reducer';
import userRole from 'app/entities/user-role/user-role.reducer';
import complaintCategory from 'app/entities/complaint-category/complaint-category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  cityService,
  cityServiceState,
  userRole,
  complaintCategory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
