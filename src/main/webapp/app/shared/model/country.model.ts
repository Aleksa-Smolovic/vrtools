export interface ICountry {
  id?: number;
  name?: string;
  populationCount?: number;
  currency?: string;
}

export const defaultValue: Readonly<ICountry> = {};
