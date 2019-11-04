import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedRangeDeleteDialogComponent } from 'app/entities/allocated-range/allocated-range-delete-dialog.component';
import { AllocatedRangeService } from 'app/entities/allocated-range/allocated-range.service';

describe('Component Tests', () => {
  describe('AllocatedRange Management Delete Component', () => {
    let comp: AllocatedRangeDeleteDialogComponent;
    let fixture: ComponentFixture<AllocatedRangeDeleteDialogComponent>;
    let service: AllocatedRangeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedRangeDeleteDialogComponent]
      })
        .overrideTemplate(AllocatedRangeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedRangeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedRangeService);
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
