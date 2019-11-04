import { IProvisioner } from 'app/shared/model/provisioner.model';

export interface IAllocatedUnicastRange {
  id?: number;
  lowAddress?: string;
  highAddress?: string;
  provisioner?: IProvisioner;
}

export class AllocatedUnicastRange implements IAllocatedUnicastRange {
  constructor(public id?: number, public lowAddress?: string, public highAddress?: string, public provisioner?: IProvisioner) {}
}
