import { IOrganizationProduct } from 'app/shared/model/organization-product.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string;
  organizationProducts?: IOrganizationProduct[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public organizationProducts?: IOrganizationProduct[]
  ) {}
}
