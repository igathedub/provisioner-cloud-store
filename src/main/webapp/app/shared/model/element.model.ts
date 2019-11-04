import { IModel } from 'app/shared/model/model.model';
import { INode } from 'app/shared/model/node.model';

export interface IElement {
  id?: number;
  index?: number;
  location?: string;
  name?: string;
  models?: IModel[];
  node?: INode;
}

export class Element implements IElement {
  constructor(
    public id?: number,
    public index?: number,
    public location?: string,
    public name?: string,
    public models?: IModel[],
    public node?: INode
  ) {}
}
