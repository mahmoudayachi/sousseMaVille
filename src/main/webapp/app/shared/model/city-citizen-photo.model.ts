export interface ICityCitizenPhoto {
  id?: number;
  imageContentType?: string | null;
  image?: string | null;
}

export const defaultValue: Readonly<ICityCitizenPhoto> = {};
