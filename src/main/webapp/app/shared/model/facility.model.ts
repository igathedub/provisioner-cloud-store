import { ICustomer } from 'app/shared/model/customer.model';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface IFacility {
  id?: number;
  name?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  country?: string;
  customer?: ICustomer;
  networks?: INetworkConfiguration[];
}

export class Facility implements IFacility {
  constructor(
    public id?: number,
    public name?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public country?: string,
    public customer?: ICustomer,
    public networks?: INetworkConfiguration[]
  ) {}
}
