import { IOrganizationProduct } from 'app/shared/model/organization-product.model';

export interface IOrganization {
  id?: number;
  name?: string;
  description?: string;
  organizationProducts?: IOrganizationProduct[];
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public organizationProducts?: IOrganizationProduct[]
  ) {}
}
