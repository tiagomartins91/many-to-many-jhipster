import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganizationProduct } from 'app/shared/model/organization-product.model';
import { OrganizationProductService } from './organization-product.service';

@Component({
  selector: 'jhi-organization-product-delete-dialog',
  templateUrl: './organization-product-delete-dialog.component.html'
})
export class OrganizationProductDeleteDialogComponent {
  organizationProduct: IOrganizationProduct;

  constructor(
    protected organizationProductService: OrganizationProductService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.organizationProductService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'organizationProductListModification',
        content: 'Deleted an organizationProduct'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-organization-product-delete-popup',
  template: ''
})
export class OrganizationProductDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ organizationProduct }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrganizationProductDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.organizationProduct = organizationProduct;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/organization-product', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/organization-product', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
