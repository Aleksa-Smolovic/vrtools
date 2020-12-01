export interface ICity {
  id?: number;
  name?: string;
  population?: number;
  isCapital?: boolean;
  countryName?: string;
  countryId?: number;
}

export const defaultValue: Readonly<ICity> = {
  isCapital: false,
};
