import { IProvisioner } from 'app/shared/model/provisioner.model';

export interface IAllocatedRange {
  id?: number;
  lowAddress?: string;
  highAddress?: string;
  provisioner?: IProvisioner;
  provisioner?: IProvisioner;
  provisioner?: IProvisioner;
}

export class AllocatedRange implements IAllocatedRange {
  constructor(
    public id?: number,
    public lowAddress?: string,
    public highAddress?: string,
    public provisioner?: IProvisioner,
    public provisioner?: IProvisioner,
    public provisioner?: IProvisioner
  ) {}
}
