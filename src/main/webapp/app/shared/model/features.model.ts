import { INode } from 'app/shared/model/node.model';

export interface IFeatures {
  id?: number;
  proxy?: number;
  friend?: number;
  relay?: number;
  lowPower?: number;
  node?: INode;
}

export class Features implements IFeatures {
  constructor(
    public id?: number,
    public proxy?: number,
    public friend?: number,
    public relay?: number,
    public lowPower?: number,
    public node?: INode
  ) {}
}
