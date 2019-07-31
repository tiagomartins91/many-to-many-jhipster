import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrganizationProduct } from 'app/shared/model/organization-product.model';
import { OrganizationProductService } from './organization-product.service';
import { OrganizationProductComponent } from './organization-product.component';
import { OrganizationProductDetailComponent } from './organization-product-detail.component';
import { OrganizationProductUpdateComponent } from './organization-product-update.component';
import { OrganizationProductDeletePopupComponent } from './organization-product-delete-dialog.component';
import { IOrganizationProduct } from 'app/shared/model/organization-product.model';

@Injectable({ providedIn: 'root' })
export class OrganizationProductResolve implements Resolve<IOrganizationProduct> {
  constructor(private service: OrganizationProductService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrganizationProduct> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrganizationProduct>) => response.ok),
        map((organizationProduct: HttpResponse<OrganizationProduct>) => organizationProduct.body)
      );
    }
    return of(new OrganizationProduct());
  }
}

export const organizationProductRoute: Routes = [
  {
    path: '',
    component: OrganizationProductComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'OrganizationProducts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrganizationProductDetailComponent,
    resolve: {
      organizationProduct: OrganizationProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganizationProducts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrganizationProductUpdateComponent,
    resolve: {
      organizationProduct: OrganizationProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganizationProducts'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrganizationProductUpdateComponent,
    resolve: {
      organizationProduct: OrganizationProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganizationProducts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const organizationProductPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrganizationProductDeletePopupComponent,
    resolve: {
      organizationProduct: OrganizationProductResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'OrganizationProducts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
