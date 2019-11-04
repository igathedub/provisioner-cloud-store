import { ICustomer } from 'app/shared/model/customer.model';
import { IRole } from 'app/shared/model/role.model';

export interface IAppUser {
  id?: number;
  name?: string;
  password?: string;
  email?: string;
  domain?: string;
  customer?: ICustomer;
  roles?: IRole[];
}

export class AppUser implements IAppUser {
  constructor(
    public id?: number,
    public name?: string,
    public password?: string,
    public email?: string,
    public domain?: string,
    public customer?: ICustomer,
    public roles?: IRole[]
  ) {}
}
