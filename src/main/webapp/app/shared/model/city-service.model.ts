import { ICityServiceState } from 'app/shared/model/city-service-state.model';
import { IUserRole } from 'app/shared/model/user-role.model';

export interface ICityService {
  id?: number;
  title?: string;
  description?: string;
  tooltip?: string;
  icon?: string;
  order?: number;
  cityservicestate?: ICityServiceState;
  userroles?: IUserRole[];
}

export const defaultValue: Readonly<ICityService> = {};
