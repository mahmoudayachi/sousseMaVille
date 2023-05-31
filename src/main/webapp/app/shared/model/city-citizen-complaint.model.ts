import dayjs from 'dayjs';
import { IComplaintCategory } from 'app/shared/model/complaint-category.model';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';
import { Complaintstate } from 'app/shared/model/enumerations/complaintstate.model';

export interface ICityCitizenComplaint {
  id?: number;
  address?: string;
  description?: string;
  date?: string;
  sharewithpublic?: boolean | null;
  complaintstate?: Complaintstate;
  firstname?: string | null;
  lastname?: string | null;
  email?: string | null;
  phonenumber?: string | null;
  googlemapsx?: string;
  googlemapy?: string;
  complaintCategory?: IComplaintCategory;
  cityCitizenPhotos?: ICityCitizenPhoto[];
}

export const defaultValue: Readonly<ICityCitizenComplaint> = {
  sharewithpublic: false,
};
