import { IAppUser } from 'app/shared/model/app-user.model';

export interface IRole {
  id?: number;
  name?: string;
  description?: string;
  appUser?: IAppUser;
}

export class Role implements IRole {
  constructor(public id?: number, public name?: string, public description?: string, public appUser?: IAppUser) {}
}
