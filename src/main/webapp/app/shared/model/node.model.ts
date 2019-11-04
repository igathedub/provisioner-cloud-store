import { IFeatures } from 'app/shared/model/features.model';
import { IElement } from 'app/shared/model/element.model';
import { IKeyIndex } from 'app/shared/model/key-index.model';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';

export interface INode {
  id?: number;
  unicastAddress?: string;
  configComplete?: boolean;
  defaultTTL?: number;
  cid?: string;
  blacklisted?: boolean;
  uUID?: string;
  security?: string;
  crpl?: string;
  name?: string;
  deviceKey?: string;
  vid?: string;
  pid?: string;
  features?: IFeatures;
  elements?: IElement[];
  netKeys?: IKeyIndex[];
  appKeys?: IKeyIndex[];
  networkConfiguration?: INetworkConfiguration;
}

export class Node implements INode {
  constructor(
    public id?: number,
    public unicastAddress?: string,
    public configComplete?: boolean,
    public defaultTTL?: number,
    public cid?: string,
    public blacklisted?: boolean,
    public uUID?: string,
    public security?: string,
    public crpl?: string,
    public name?: string,
    public deviceKey?: string,
    public vid?: string,
    public pid?: string,
    public features?: IFeatures,
    public elements?: IElement[],
    public netKeys?: IKeyIndex[],
    public appKeys?: IKeyIndex[],
    public networkConfiguration?: INetworkConfiguration
  ) {
    this.configComplete = this.configComplete || false;
    this.blacklisted = this.blacklisted || false;
  }
}
