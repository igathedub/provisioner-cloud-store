import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface IAppKey {
  id?: number;
  key?: string;
  name?: string;
  boundNetKey?: number;
  index?: number;
  networkConfiguration?: INetworkConfiguration;
}

export class AppKey implements IAppKey {
  constructor(
    public id?: number,
    public key?: string,
    public name?: string,
    public boundNetKey?: number,
    public index?: number,
    public networkConfiguration?: INetworkConfiguration
  ) {}
}
