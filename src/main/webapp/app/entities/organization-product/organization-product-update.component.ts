import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOrganizationProduct, OrganizationProduct } from 'app/shared/model/organization-product.model';
import { OrganizationProductService } from './organization-product.service';
import { IOrganization } from 'app/shared/model/organization.model';
import { OrganizationService } from 'app/entities/organization';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
  selector: 'jhi-organization-product-update',
  templateUrl: './organization-product-update.component.html'
})
export class OrganizationProductUpdateComponent implements OnInit {
  isSaving: boolean;

  organizations: IOrganization[];

  products: IProduct[];

  editForm = this.fb.group({
    id: [],
    status: [],
    organizationId: [],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected organizationProductService: OrganizationProductService,
    protected organizationService: OrganizationService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ organizationProduct }) => {
      this.updateForm(organizationProduct);
    });
    this.organizationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrganization[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrganization[]>) => response.body)
      )
      .subscribe((res: IOrganization[]) => (this.organizations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduct[]>) => response.body)
      )
      .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(organizationProduct: IOrganizationProduct) {
    this.editForm.patchValue({
      id: organizationProduct.id,
      status: organizationProduct.status,
      organizationId: organizationProduct.organizationId,
      productId: organizationProduct.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const organizationProduct = this.createFromForm();
    if (organizationProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.organizationProductService.update(organizationProduct));
    } else {
      this.subscribeToSaveResponse(this.organizationProductService.create(organizationProduct));
    }
  }

  private createFromForm(): IOrganizationProduct {
    return {
      ...new OrganizationProduct(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      organizationId: this.editForm.get(['organizationId']).value,
      productId: this.editForm.get(['productId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganizationProduct>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackOrganizationById(index: number, item: IOrganization) {
    return item.id;
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }
}
