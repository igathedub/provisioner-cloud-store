import { IPublish } from 'app/shared/model/publish.model';
import { IElement } from 'app/shared/model/element.model';

export interface IModel {
  id?: number;
  modelId?: string;
  subscribe?: string;
  bind?: string;
  publish?: IPublish;
  element?: IElement;
}

export class Model implements IModel {
  constructor(
    public id?: number,
    public modelId?: string,
    public subscribe?: string,
    public bind?: string,
    public publish?: IPublish,
    public element?: IElement
  ) {}
}
