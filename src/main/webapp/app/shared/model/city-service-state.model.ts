import { state } from 'app/shared/model/enumerations/state.model';

export interface ICityServiceState {
  id?: number;
  name?: state;
}

export const defaultValue: Readonly<ICityServiceState> = {};
