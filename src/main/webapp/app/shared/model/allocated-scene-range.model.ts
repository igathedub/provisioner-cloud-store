import { IProvisioner } from 'app/shared/model/provisioner.model';

export interface IAllocatedSceneRange {
  id?: number;
  lowAddress?: string;
  highAddress?: string;
  provisioner?: IProvisioner;
}

export class AllocatedSceneRange implements IAllocatedSceneRange {
  constructor(public id?: number, public lowAddress?: string, public highAddress?: string, public provisioner?: IProvisioner) {}
}
