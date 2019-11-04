import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { MeshGroupDetailComponent } from 'app/entities/mesh-group/mesh-group-detail.component';
import { MeshGroup } from 'app/shared/model/mesh-group.model';

describe('Component Tests', () => {
  describe('MeshGroup Management Detail Component', () => {
    let comp: MeshGroupDetailComponent;
    let fixture: ComponentFixture<MeshGroupDetailComponent>;
    const route = ({ data: of({ meshGroup: new MeshGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [MeshGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeshGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeshGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meshGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
