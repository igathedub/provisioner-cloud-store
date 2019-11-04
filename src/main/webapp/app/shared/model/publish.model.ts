import { IRetransmit } from 'app/shared/model/retransmit.model';
import { IModel } from 'app/shared/model/model.model';

export interface IPublish {
  id?: number;
  index?: number;
  credentials?: number;
  ttl?: number;
  period?: number;
  address?: string;
  retransmit?: IRetransmit;
  model?: IModel;
}

export class Publish implements IPublish {
  constructor(
    public id?: number,
    public index?: number,
    public credentials?: number,
    public ttl?: number,
    public period?: number,
    public address?: string,
    public retransmit?: IRetransmit,
    public model?: IModel
  ) {}
}
