import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppTestSharedModule } from 'app/shared';
import {
  OrganizationProductComponent,
  OrganizationProductDetailComponent,
  OrganizationProductUpdateComponent,
  OrganizationProductDeletePopupComponent,
  OrganizationProductDeleteDialogComponent,
  organizationProductRoute,
  organizationProductPopupRoute
} from './';

const ENTITY_STATES = [...organizationProductRoute, ...organizationProductPopupRoute];

@NgModule({
  imports: [AppTestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OrganizationProductComponent,
    OrganizationProductDetailComponent,
    OrganizationProductUpdateComponent,
    OrganizationProductDeleteDialogComponent,
    OrganizationProductDeletePopupComponent
  ],
  entryComponents: [
    OrganizationProductComponent,
    OrganizationProductUpdateComponent,
    OrganizationProductDeleteDialogComponent,
    OrganizationProductDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestOrganizationProductModule {}
