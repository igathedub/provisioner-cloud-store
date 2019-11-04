import { IProvisioner } from 'app/shared/model/provisioner.model';

export interface IAllocatedGroupRange {
  id?: number;
  lowAddress?: string;
  highAddress?: string;
  provisioner?: IProvisioner;
}

export class AllocatedGroupRange implements IAllocatedGroupRange {
  constructor(public id?: number, public lowAddress?: string, public highAddress?: string, public provisioner?: IProvisioner) {}
}
