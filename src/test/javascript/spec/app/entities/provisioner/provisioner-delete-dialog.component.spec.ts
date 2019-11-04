import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ProvisionerDeleteDialogComponent } from 'app/entities/provisioner/provisioner-delete-dialog.component';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';

describe('Component Tests', () => {
  describe('Provisioner Management Delete Component', () => {
    let comp: ProvisionerDeleteDialogComponent;
    let fixture: ComponentFixture<ProvisionerDeleteDialogComponent>;
    let service: ProvisionerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ProvisionerDeleteDialogComponent]
      })
        .overrideTemplate(ProvisionerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProvisionerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProvisionerService);
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
