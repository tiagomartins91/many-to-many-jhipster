import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'organization',
        loadChildren: './organization/organization.module#AppTestOrganizationModule'
      },
      {
        path: 'organization-product',
        loadChildren: './organization-product/organization-product.module#AppTestOrganizationProductModule'
      },
      {
        path: 'product',
        loadChildren: './product/product.module#AppTestProductModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTestEntityModule {}
