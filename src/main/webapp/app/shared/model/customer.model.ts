import { IFacility } from 'app/shared/model/facility.model';
import { IAppUser } from 'app/shared/model/app-user.model';

export interface ICustomer {
  id?: number;
  name?: string;
  address?: string;
  facilities?: IFacility[];
  users?: IAppUser[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public address?: string,
    public facilities?: IFacility[],
    public users?: IAppUser[]
  ) {}
}
