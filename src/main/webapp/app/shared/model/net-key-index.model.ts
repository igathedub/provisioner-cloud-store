import { INode } from 'app/shared/model/node.model';

export interface INetKeyIndex {
  id?: number;
  index?: number;
  updated?: boolean;
  node?: INode;
}

export class NetKeyIndex implements INetKeyIndex {
  constructor(public id?: number, public index?: number, public updated?: boolean, public node?: INode) {
    this.updated = this.updated || false;
  }
}
