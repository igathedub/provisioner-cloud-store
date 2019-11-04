import { IPublish } from 'app/shared/model/publish.model';

export interface IRetransmit {
  id?: number;
  count?: number;
  interval?: number;
  publish?: IPublish;
}

export class Retransmit implements IRetransmit {
  constructor(public id?: number, public count?: number, public interval?: number, public publish?: IPublish) {}
}
