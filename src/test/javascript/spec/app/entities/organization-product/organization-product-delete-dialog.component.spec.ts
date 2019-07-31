/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestTestModule } from '../../../test.module';
import { OrganizationProductDeleteDialogComponent } from 'app/entities/organization-product/organization-product-delete-dialog.component';
import { OrganizationProductService } from 'app/entities/organization-product/organization-product.service';

describe('Component Tests', () => {
  describe('OrganizationProduct Management Delete Component', () => {
    let comp: OrganizationProductDeleteDialogComponent;
    let fixture: ComponentFixture<OrganizationProductDeleteDialogComponent>;
    let service: OrganizationProductService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestTestModule],
        declarations: [OrganizationProductDeleteDialogComponent]
      })
        .overrideTemplate(OrganizationProductDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganizationProductDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganizationProductService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
