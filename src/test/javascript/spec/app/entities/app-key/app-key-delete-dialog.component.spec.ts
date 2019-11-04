import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AppKeyDeleteDialogComponent } from 'app/entities/app-key/app-key-delete-dialog.component';
import { AppKeyService } from 'app/entities/app-key/app-key.service';

describe('Component Tests', () => {
  describe('AppKey Management Delete Component', () => {
    let comp: AppKeyDeleteDialogComponent;
    let fixture: ComponentFixture<AppKeyDeleteDialogComponent>;
    let service: AppKeyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AppKeyDeleteDialogComponent]
      })
        .overrideTemplate(AppKeyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AppKeyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppKeyService);
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
