import { IUser } from 'app/shared/model/user.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IUserRole {
  id?: number;
  name?: Role;
  userroles?: IUser[];
}

export const defaultValue: Readonly<IUserRole> = {};
