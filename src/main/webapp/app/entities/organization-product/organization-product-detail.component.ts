import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganizationProduct } from 'app/shared/model/organization-product.model';

@Component({
  selector: 'jhi-organization-product-detail',
  templateUrl: './organization-product-detail.component.html'
})
export class OrganizationProductDetailComponent implements OnInit {
  organizationProduct: IOrganizationProduct;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ organizationProduct }) => {
      this.organizationProduct = organizationProduct;
    });
  }

  previousState() {
    window.history.back();
  }
}
