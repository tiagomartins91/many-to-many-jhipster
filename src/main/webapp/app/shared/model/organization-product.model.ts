export interface IOrganizationProduct {
  id?: number;
  status?: boolean;
  organizationId?: number;
  productId?: number;
}

export class OrganizationProduct implements IOrganizationProduct {
  constructor(public id?: number, public status?: boolean, public organizationId?: number, public productId?: number) {
    this.status = this.status || false;
  }
}
